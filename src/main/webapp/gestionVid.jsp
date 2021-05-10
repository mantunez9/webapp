<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" type="isdcm.tomcat.webapp.model.User"/>
<%--
  Created by IntelliJ IDEA.
  User: mantu
  Date: 25/02/2021
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="css/style.css">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <meta http-equiv="Content-Type"
          content="text/html; charset=windows-1256">
    <title>Video</title>
    <link rel="stylesheet" type="text/css" href="css/dropdown.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
    <link href="https://unpkg.com/video.js/dist/video-js.min.css" rel="stylesheet">
    <script src="https://unpkg.com/video.js/dist/video.min.js"></script>
    <script src="video/Youtube.min.js"></script>
</head>
<body>
<div class="dropdown">
    <button class="dropbtn">${user.nickName}</button>
    <div class="dropdown-content">
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>
<div>
    <button onclick="location.href='${pageContext.request.contextPath}/securityXML'" type="button">
        XML Encryption/Decryption</button>
</div>
<div>
    <button onclick="location.href='${pageContext.request.contextPath}/securityContent'" type="button">
        Content Encryption/Decryption</button>
</div>
<div class="video-page">
    <div class="video-form">
        <h1>Video Registration</h1>
        <form action="video" method="post">
            <p>
                <label for="titulo">Title: </label>
                <input id="titulo" name="titulo" required="required" type="text" placeholder="title"/>
            </p>
            <p>
                <label for="autor">Author:</label>
                <input id="autor" name="autor" required="required" type="text"
                       placeholder="author"/>
            </p>
            <p>
                <label for="date">Creation date:</label>
                <input id="date" name="date" required="required" type="date" placeholder="DD/MM/YYYY"/>
            </p>
            <p>
                <label for="duracion">Duration time:</label>
                <input id="duracion" name="duracion" required="required" type="text"
                       pattern="^(?:(?:([01]?\d|2[0-3]):)([0-5]?\d):)([0-5]?\d)$"
                       placeholder="mm:ss:nn"/>
            </p>
            <p>
                <label for="reproducciones">Number of reproductions:</label>
                <input id="reproducciones" name="reproducciones" required="required" type="number" min="0"
                       placeholder="number of reproductions"/>
            </p>
            <p>
                <label for="descripcion">Description:</label>
                <input id="descripcion" name="descripcion" required="required" type="text" placeholder="description"/>
            </p>
            <p>
                <label for="formato">Format: </label>
                <input id="formato" name="formato" required="required" type="text" placeholder="mp4"/>
            </p>
            <p>
                <label for="url">URL: </label>
                <input id="url" name="url" type="text" placeholder="www.youtube.com/example"/>
            </p>
            <button type="submit">create</button>
        </form>
    </div>
</div>
<div style="padding: 8% 0 0;">
    <div style="background: #FFFFFF; padding: 15px; text-align: center;">
        <div class="video-form" style="box-shadow: none; max-width: 50%;margin: unset; padding: unset;">
            <h1>Search</h1>
            <form action="video" method="get">
                <p style="display:flex; flex-direction: row;">
                    <label style="width: 15%">By Title: </label>
                    <input id="byTitle" name="byTitle" type="text" placeholder="title" onchange="blockOtherInput('t')"/>
                </p>
                <p style="display:flex; flex-direction: row;">
                    <label style="width: 15%">By Author: </label>
                    <input id="byAuthor" name="byAuthor" type="text" placeholder="author"
                           onchange="blockOtherInput('a')"/>
                </p>
                <p style="display:flex; flex-direction: row;">
                    <label style="width: 15%">By Creation Date: </label>
                    <input id="byCreationDate" name="byCreationDate" type="text"
                           placeholder="YYYY-MM-DD, put XX if month/day is not required"
                           onchange="blockOtherInput('cd')"
                           pattern="(?:(?:([0-9]{4})-)(0[1-9]|1[012]|XX)-)(?:XX)|(?:(?!XX)(?:([0-9]{4})-)(?:0[1-9]|1[0-2])-(?:(0[1-9]|1[0-9]|2[0-9]|3[01]|XX)))"/>
                </p>
                <button type="submit" style="width: 50%;">find</button>
            </form>
            <script>
                function blockOtherInput(id) {
                    var tInput = document.getElementById("byTitle");
                    var aInput = document.getElementById("byAuthor");
                    var cdInput = document.getElementById("byCreationDate");
                    if (id == "t" && tInput.value != "") {
                        aInput.setAttribute("readonly", true);
                        cdInput.setAttribute("readonly", true);
                    } else if (id == "a" && aInput.value != "") {
                        tInput.setAttribute("readonly", true);
                        cdInput.setAttribute("readonly", true);
                    } else if (id == "cd" && cdInput.value != "") {
                        tInput.setAttribute("readonly", true);
                        aInput.setAttribute("readonly", true);
                    } else {
                        tInput.removeAttribute("readonly");
                        aInput.removeAttribute("readonly");
                        cdInput.removeAttribute("readonly");
                    }
                }
            </script>
        </div>
        <table id="videoTable" class="flat-table">
            <h1>List of videos</h1>
            <thead>
            <tr>
                <th onclick="sortTable(0)">ID</th>
                <th onclick="sortTable(1)">Title</th>
                <th onclick="sortTable(2)">Author</th>
                <th onclick="sortTable(3)">Creation date</th>
                <th onclick="sortTable(4)">Time</th>
                <th onclick="sortTable(5)">Number of reproductions</th>
                <th onclick="sortTable(6)">Description</th>
                <th onclick="sortTable(7)">Format</th>
                <th onclick="sortTable(8)">Url</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${videos}" var="video">
                <tr>
                    <td>${video.videoId}</td>
                    <td>${video.tittle}</td>
                    <td>${video.author}</td>
                    <td>${video.creationDate}</td>
                    <td>${video.duration}</td>
                    <td>${video.reproduction}</td>
                    <td>${video.description}</td>
                    <td>${video.format}</td>
                    <td>${video.url}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <script>
            function sortTable(n) {
                var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
                table = document.getElementById("videoTable");
                switching = true;
                dir = "asc";
                while (switching) {
                    switching = false;
                    rows = table.rows;
                    for (i = 1; i < (rows.length - 1); i++) {
                        shouldSwitch = false;
                        x = rows[i].getElementsByTagName("TD")[n];
                        y = rows[i + 1].getElementsByTagName("TD")[n];
                        if (dir === "asc") {
                            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                                shouldSwitch = true;
                                break;
                            }
                        } else if (dir === "desc") {
                            if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                                shouldSwitch = true;
                                break;
                            }
                        }
                    }
                    if (shouldSwitch) {
                        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                        switching = true;
                        switchcount++;
                    } else {
                        if (switchcount === 0 && dir === "asc") {
                            dir = "desc";
                            switching = true;
                        }
                    }
                }
            }
        </script>
        <br/>
        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog modal-xl">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Video Playback</h4>
                    </div>
                    <div class="modal-body">
                        <div id="videoDiv" hidden>
                            <video
                                    id="vid-local"
                                    class="video-js vjs-default-skin"
                                    controls
                                    preload="auto"
                                    poster="https://www.tshiamisotrust.com/wp-content/uploads/2020/11/videos.png"
                                    width="640" height="264"
                                    data-setup='{}'>
                                <source src="//vjs.zencdn.net/v/oceans.mp4" type="video/mp4"></source>
                                <p class="vjs-no-js">
                                    To view this video please enable JavaScript, and consider upgrading to a
                                    web browser that
                                    <a href="https://videojs.com/html5-video-support/" target="_blank">
                                        supports HTML5 video
                                    </a>
                                </p>
                            </video>
                        </div>
                        <video id="vid-yt"
                               hidden
                               class="video-js vjs-default-skin"
                               controls
                               poster="https://www.tshiamisotrust.com/wp-content/uploads/2020/11/videos.png"
                               width="640" height="264"
                               data-setup='{ "techOrder": ["youtube"], "sources": [{ "type": "video/youtube", "src": "https://www.youtube.com/watch?v=ientnbMoFkw"}], "youtube": { "iv_load_policy": 1 } }'
                        >
                        </video>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
        <script>
            jQuery(document).ready(function ($) {

                var id = 0;
                var row = 0;

                $('#videoTable').DataTable({
                    searching: false,
                    responsive: true,
                    "autoWidth": false,
                });
                var table = $('#videoTable').DataTable();
                $('#videoTable tbody').on('click', 'tr', function () {
                    $(".modal-body div span").text("");
                    var url = table.row(this).data()[8];
                    var type = table.row(this).data()[7];
                    id = table.row(this).data()[0];
                    row = table.row(this);
                    if (url) {
                        if (type == "youtube") {
                            // hide local video player
                            $('#videoDiv').attr('hidden', true);
                            // change youtube video player source and show it
                            videojs("vid-yt").src({type: "video/youtube", src: url});
                            $("#vid-yt").removeAttr('hidden');
                        } else {
                            // hide youtube video player
                            $("#vid-yt").attr('hidden', true);
                            // change local video player source and show it
                            videojs("vid-local").src({type: "video/"+type, src: url});
                            $('#videoDiv').removeAttr('hidden');
                        }
                        $("#myModal").modal("show");
                    }
                });

                $('.vjs-big-play-button').on("click", function () {

                    var url = "http://localhost:8080/webapp_war_exploded/video";

                    $.ajax({
                        url: url + "?id=" + id,
                        cache: false,
                        type: 'PUT',
                        success: function (data) {
                            var reproductionTemp = row.data();
                            reproductionTemp[5] = data.reproductions;
                            row.data(reproductionTemp).invalidate();
                        }
                    });

                });

            });
        </script>
    </div>
</div>
</body>
</html>
