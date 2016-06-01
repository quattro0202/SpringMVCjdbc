<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="mainmenu">
  <ul>
    <!-- Пункт меню 1 -->
    <li class="parent"><a href="#">Постачальники</a>
      <ul>
        <li><a href="/suppliers/list">Список</a></li>
        <li><a href="/suppliers/search">Знайти/редагувати</a></li>
        <li><a href="/suppliers/add">Додати</a></li>
      </ul>
    </li>
    <!-- Пункт меню 2 -->
    <li class="parent"><a href="#">Співробітники</a>
      <ul>
        <li><a href="/employees/list">Список</a>
        <li><a href="/employees/search">Знайти/редагувати</a></li>
        <li><a href="/employees/add">Додати</a></li>
      </ul>
    </li>
    <!-- Пункт меню 3 -->
    <li class="parent"><a href="#">Замовлення на поставку</a>
      <ul>
        <li><a href="/employees/list">Список</a>
        <li><a href="/employees/search">Знайти/редагувати</a></li>
        <li><a href="/employees/add">Додати</a></li>
      </ul>
    </li>
  <!-- Конец списка -->
</div>
<!-- Конец блока #mainmenu -->
