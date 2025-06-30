<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>RSA Key Generator</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .key-container {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .btn-download {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center mb-4">RSA Key Generator</h1>

    <div class="text-center mb-4">
        <form action="generateKey" method="post" class="d-flex justify-content-center align-items-center gap-3 flex-wrap">
            <label for="keySize" class="form-label m-0">Select Key Size:</label>
            <select name="keySize" id="keySize" class="form-select w-auto">
                <option value="512">512 bits</option>
                <option value="1024">1024 bits</option>
                <option value="2048" selected>2048 bits</option>
                <option value="4096">4096 bits</option>
            </select>
            <button type="submit" class="btn btn-primary">Generate New Key Pair</button>
        </form>
    </div>


    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">${error}</div>
    </c:if>

    <c:if test="${not empty privateKey}">
        <div class="key-container">
            <h4>Private Key</h4>
            <pre class="bg-light p-3 rounded">${privateKey}</pre>
            <a href="download?type=private" class="btn btn-success btn-download">Download Private Key</a>

        </div>
    </c:if>

    <c:if test="${not empty publicKey}">
        <div class="key-container">
            <h4>Public Key</h4>
            <pre class="bg-light p-3 rounded">${publicKey}</pre>
            <a href="download?type=public" class="btn btn-success btn-download">Download Public Key</a>


        </div>
    </c:if>
    <div class="text-center mt-4">
        <a href="checkout" class="btn btn-secondary">Quay về trang mua hàng</a>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
