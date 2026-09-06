import heapq
from collections import deque
import random
import math
import time

# ==========================================
# CẤU TRÚC ĐỒ THỊ VÀ HÀM HEURISTIC MẪU
# Mạng lưới 34x34 = 1156 nút. Mỗi nút có tên (r,c).
# Chi phí mỗi cạnh = 1. Heuristic = khoảng cách Manhattan tới đích.
# ==========================================
GRID_SIZE = 34
GRAPH = {}
HEURISTICS = {}

# === STOP POINT: chặn tràn bộ nhớ & CPU trên lưới 34x34 ===
SAFE_NODE_LIMIT = 1_000_000          # frontier/explored tối đa 1M nút (~80 MB)
SAFE_RUNTIME_SEC = 30                # timeout an toàn cho mỗi lệnh BFS/DFS/A*
SAFE_IDS_MAX_DEPTH = 66              # = 2*(GRID_SIZE-1) = Manhattan từ (0,0) -> (33,33)

def _neighbors(r, c):
    n = []
    if r > 0: n.append((r - 1, c))
    if r < GRID_SIZE - 1: n.append((r + 1, c))
    if c > 0: n.append((r, c - 1))
    if c < GRID_SIZE - 1: n.append((r, c + 1))
    return n

for r in range(GRID_SIZE):
    for c in range(GRID_SIZE):
        node = f"{r},{c}"
        GRAPH[node] = {}
        for nr, nc in _neighbors(r, c):
            GRAPH[node][f"{nr},{nc}"] = 1
        HEURISTICS[node] = (GRID_SIZE - 1 - r) + (GRID_SIZE - 1 - c)

START = "0,0"
GOAL = f"{GRID_SIZE - 1},{GRID_SIZE - 1}"
TOTAL_NODES = GRID_SIZE * GRID_SIZE
OPTIMAL_COST = (GRID_SIZE - 1) * 2

# ==========================================
# CẤU TRÚC NODE CHUNG
# ==========================================
class Node:
    __slots__ = ("state", "parent", "g", "h", "f", "depth")
    def __init__(self, state, parent=None, g=0, h=0, f_type='a_star'):
        self.state = state
        self.parent = parent
        self.g = g
        self.h = h
        self.depth = 0 if parent is None else parent.depth + 1
        if f_type == 'greedy':
            self.f = h
        elif f_type == 'a_star' or f_type == 'bestfs':
            self.f = g + h
        else:
            self.f = 0

    def __lt__(self, other):
        return self.f < other.f

    def get_path(self):
        path, current = [], self
        while current:
            path.append(current.state)
            current = current.parent
        return path[::-1], self.g

# ==========================================
# Ngoại lệ riêng — báo vượt ngưỡng an toàn
# ==========================================
class SafetyAbort(Exception):
    pass

def _guard(nodes_explored, frontier_size, t0, name):
    if frontier_size > SAFE_NODE_LIMIT:
        raise SafetyAbort(f"{name} frontier={frontier_size} vuot SAFE_NODE_LIMIT={SAFE_NODE_LIMIT}")
    if nodes_explored > SAFE_NODE_LIMIT * 5:
        raise SafetyAbort(f"{name} explored={nodes_explored} vuot nguong")
    if (time.time() - t0) > SAFE_RUNTIME_SEC:
        raise SafetyAbort(f"{name} vuot {SAFE_RUNTIME_SEC}s timeout")

# 1. BREADTH-FIRST SEARCH
def bfs(start, goal):
    t0 = time.time()
    frontier = deque([Node(start)])
    explored = set([start])
    nodes_explored = 0
    while frontier:
        node = frontier.popleft()
        nodes_explored += 1
        if node.state == goal:
            return node.get_path(), nodes_explored
        for neighbor, cost in GRAPH.get(node.state, {}).items():
            if neighbor not in explored:
                explored.add(neighbor)
                frontier.append(Node(neighbor, node, node.g + cost))
        _guard(nodes_explored, len(frontier), t0, "BFS")
    return None, nodes_explored

# 2. DEPTH-FIRST SEARCH (gio CO explored -> an toan)
def dfs(start, goal):
    t0 = time.time()
    frontier = [Node(start)]
    explored = set([start])
    nodes_explored = 0
    while frontier:
        node = frontier.pop()
        nodes_explored += 1
        if node.state == goal:
            return node.get_path(), nodes_explored
        for neighbor, cost in GRAPH.get(node.state, {}).items():
            if neighbor not in explored:
                explored.add(neighbor)
                frontier.append(Node(neighbor, node, node.g + cost))
        _guard(nodes_explored, len(frontier), t0, "DFS")
    return None, nodes_explored

# 3. ITERATIVE DEEPENING SEARCH (dung BFS moi depth de tranh bi gap' chan boi global explored)
def bfs_limited(start, goal, limit):
    t0 = time.time()
    frontier = deque([Node(start)])
    explored = set([start])
    nodes_explored = 0
    while frontier:
        node = frontier.popleft()
        nodes_explored += 1
        if node.state == goal:
            return node.get_path(), nodes_explored
        if node.depth < limit:
            for neighbor, cost in GRAPH.get(node.state, {}).items():
                if neighbor not in explored:
                    explored.add(neighbor)
                    frontier.append(Node(neighbor, node, node.g + cost))
        _guard(nodes_explored, len(frontier), t0, f"BFS-limit={limit}")
    return None, nodes_explored

def ids(start, goal, max_depth=SAFE_IDS_MAX_DEPTH):
    total_nodes = 0
    for depth in range(max_depth + 1):
        result, nodes = bfs_limited(start, goal, depth)
        total_nodes += nodes
        if result is not None:
            return result, total_nodes
    return None, total_nodes

# 4-6. BestFS / Greedy / A*
def bestfs(start, goal):
    return a_star(start, goal, 'bestfs')

def greedy_search(start, goal):
    return a_star(start, goal, 'greedy')

def a_star(start, goal, f_type='a_star'):
    t0 = time.time()
    start_node = Node(start, g=0, h=HEURISTICS[start], f_type=f_type)
    frontier = [start_node]
    explored = set([start])
    nodes_explored = 0
    while frontier:
        node = heapq.heappop(frontier)
        nodes_explored += 1
        if node.state == goal:
            return node.get_path(), nodes_explored
        for neighbor, cost in GRAPH.get(node.state, {}).items():
            if neighbor not in explored:
                g = node.g + cost
                h = HEURISTICS.get(neighbor, 0)
                child = Node(neighbor, node, g, h, f_type)
                heapq.heappush(frontier, child)
        _guard(nodes_explored, len(frontier), t0, "A*")
    return None, nodes_explored

# 7. HILL CLIMBING
def hill_climbing(start):
    t0 = time.time()
    current = start
    nodes_explored = 1
    for _ in range(SAFE_NODE_LIMIT):
        neighbors = list(GRAPH.get(current, {}).keys())
        if not neighbors:
            break
        best_neighbor = min(neighbors, key=get_value)
        if get_value(best_neighbor) >= get_value(current):
            break
        current = best_neighbor
        nodes_explored += 1
        _guard(nodes_explored, 4, t0, "Hill Climbing")
    return current, nodes_explored

# 8. SIMULATED ANNEALING
def simulated_annealing(start, T=10.0, cooling_rate=0.9, min_T=0.1):
    t0 = time.time()
    current = start
    nodes_explored = 1
    steps = 0
    while T > min_T and steps < SAFE_NODE_LIMIT:
        neighbors = list(GRAPH.get(current, {}).keys())
        if not neighbors:
            break
        next_node = random.choice(neighbors)
        delta = get_value(current) - get_value(next_node)
        if delta > 0:
            current = next_node
        else:
            probability = math.exp(delta / T)
            if random.random() < probability:
                current = next_node
        T *= cooling_rate
        nodes_explored += 1
        steps += 1
        _guard(nodes_explored, 4, t0, "SA")
    return current, nodes_explored

# 9. GENETIC ALGORITHM
def fitness(chromo):
    return sum(chromo)

def genetic_algorithm(pop_size=10, chromo_length=10, generations=20):
    pop = [[random.randint(0, 1) for _ in range(chromo_length)] for _ in range(pop_size)]
    for gen in range(generations):
        pop = sorted(pop, key=fitness, reverse=True)
        if fitness(pop[0]) == chromo_length:
            return pop[0], gen
        next_gen = pop[:2]
        while len(next_gen) < pop_size:
            p1, p2 = random.sample(pop[:10], k=2)
            pt = random.randint(1, chromo_length - 1)
            c1 = p1[:pt] + p2[pt:]
            if random.random() < 0.05:
                mut_pt = random.randint(0, chromo_length - 1)
                c1[mut_pt] = 1 - c1[mut_pt]
            next_gen.append(c1)
        pop = next_gen
    return sorted(pop, key=fitness, reverse=True)[0], generations

def get_value(state):
    return HEURISTICS.get(state, float('inf'))

# ==========================================
# HAM CHAY CO BOC AN TOAN
# ==========================================
def run_search(name, fn, *args):
    t0 = time.time()
    try:
        result = fn(*args)
        dt = time.time() - t0
        if result is None:
            print(f"  {name:18} | DUNG AN TOAN (khong co ket qua)")
            return None, dt, 0
        if isinstance(result, tuple) and len(result) == 2 and isinstance(result[0], tuple):
            path_t, nodes = result
            path, cost = path_t
            tag = "OK"
            print(f"  {name:18} | thoi gian = {dt:7.3f}s | "
                  f"{path[0]} -> ... -> {path[-1]} | len={len(path):3} | "
                  f"cost={cost:3} | Nodes={nodes:4} {tag}")
            return result, dt, nodes
        else:
            if result[0] is None:
                print(f"  {name:18} | thoi gian = {dt:7.3f}s | "
                      f"khong tim thay | Nodes={result[1]:4}")
                return result, dt, result[1]
            final, nodes = result
            # GA tra ve list (chromosome), cac thuat toan khac tra ve string (state)
            if isinstance(final, list):
                print(f"  {name:18} | thoi gian = {dt:7.3f}s | "
                      f"chromosome={final[:10]}... | gen={nodes}")
            else:
                reached = "dat dich" if final == GOAL else "chua dat"
                print(f"  {name:18} | thoi gian = {dt:7.3f}s | "
                      f"final={final:6} | Nodes={nodes:4} ({reached})")
            return result, dt, nodes
    except SafetyAbort as e:
        print(f"  {name:18} | DUNG AN TOAN: {e}")
        return None, time.time() - t0, 0

if __name__ == "__main__":
    print(f"Luoi {GRID_SIZE}x{GRID_SIZE} = {TOTAL_NODES} nut | "
          f"Bat dau: {START} | Dich: {GOAL}")
    print(f"Chi phi toi uu Manhattan = {OPTIMAL_COST}")
    print(f"SAFE_NODE_LIMIT = {SAFE_NODE_LIMIT:,}")
    print(f"SAFE_RUNTIME_SEC = {SAFE_RUNTIME_SEC}s")
    print(f"SAFE_IDS_MAX_DEPTH = {SAFE_IDS_MAX_DEPTH}")
    print("=" * 60)

    print("\nChay tat ca cac thuat toan (che do khong tuong tac)...\n")
    timings = []
    for name, fn in [("BFS", lambda: bfs(START, GOAL)),
                     ("DFS", lambda: dfs(START, GOAL)),
                     ("Greedy", lambda: greedy_search(START, GOAL)),
                     ("A*", lambda: a_star(START, GOAL)),
                     ("Hill Climb", lambda: hill_climbing(START)),
                     ("SA", lambda: simulated_annealing(START))]:
        _, dt, nodes = run_search(name, fn)
        timings.append((name, dt, nodes))
    _, dt, nodes = run_search("IDS (d=66)", ids, START, GOAL)
    timings.append(("IDS", dt, nodes))
    _, dt, nodes = run_search("GA", genetic_algorithm)
    timings.append(("GA", dt, nodes))

    print("\n" + "=" * 60)
    print("BANG TOM TAT THOI GIAN")
    print("=" * 60)
    for name, dt, n in sorted(timings, key=lambda x: -x[1]):
        bar = "#" * max(1, int(dt * 10))
        print(f"  {name:14} {dt:7.3f}s  {bar}")
