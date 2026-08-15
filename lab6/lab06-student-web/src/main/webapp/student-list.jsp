<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Danh sach sinh vien</title>
</head>
<body>
    <h2>Danh sach sinh vien</h2>
    <p>Xin chao, <strong>${sessionScope.username}</strong> (${sessionScope.role})</p>

    <form action="${pageContext.request.contextPath}/students" method="get">
        <input type="text" name="search" placeholder="Tim kiem theo ho ten..." value="${searchKeyword}">
        <button type="submit">Tim kiem</button>
        <a href="${pageContext.request.contextPath}/students">Xoa tim kiem</a>
    </form>
    <br>

    <c:if test="${sessionScope.role == 'admin'}">
        <a href="${pageContext.request.contextPath}/student-form.jsp">Them sinh vien</a> |
    </c:if>
    <a href="${pageContext.request.contextPath}/dashboard.jsp">Dashboard</a> |
    <a href="${pageContext.request.contextPath}/logout">Dang xuat</a>

    <br><br>

    <c:choose>
        <c:when test="${empty students}">
            <p>Khong tim thay sinh vien nao.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <th>Ma SV</th>
                    <th>Ho ten</th>
                    <th>Lop</th>
                    <th>Email</th>
                    <c:if test="${sessionScope.role == 'admin'}">
                        <th>Thao tac</th>
                    </c:if>
                </tr>
                <c:forEach var="sv" items="${students}">
                    <tr>
                        <td>${sv.id}</td>
                        <td>${sv.name}</td>
                        <td>${sv.className}</td>
                        <td>${sv.email}</td>
                        <c:if test="${sessionScope.role == 'admin'}">
                            <td>
                                <a href="${pageContext.request.contextPath}/student-edit?id=${sv.id}">Sua</a> |
                                <a href="${pageContext.request.contextPath}/students?action=delete&id=${sv.id}"
                                   onclick="return confirm('Ban co chac chan xoa sinh vien nay?')">Xoa</a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
