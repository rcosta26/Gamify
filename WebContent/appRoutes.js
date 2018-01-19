$(document).ready(function () {


            // Api
            var apiPath = "http://localhost:8080/Gamify/api";
            //Form Login
            var username ;
            var apiKey;


            //Apps
            //Gets
            var apps = [{}];
            var appSearch;
            var appData;
            //Create
            var appID;
            var appName;
            var type;
            var description;
            //Change
            var appSearchChange;
            //Delete
            var appDelete;





            // Get all Apps
            $("#btnGetApps").click(function () {
                username = sessionStorage.getItem("username");
                apiKey = sessionStorage.getItem("apiKey");
                var url = apiPath + "/users/" + username + "/apps?apiKey=" + apiKey;
                $.ajax({
                    url: url,
                    type: "GET",
                    success: function (data) {
                        console.log(data);
                        //return an array of objects type App
                        apps = data;
                    }
                })
            });

            //Get App
            $("#btnGetApp").click(function () {
                username = sessionStorage.getItem("username");
                apiKey = sessionStorage.getItem("apiKey");
                //Get appID from input
                appSearch = $("#txtAppIDSearch").val();
                var url = apiPath + "/users/" + username + "/apps/" + appSearch + "/?apiKey=" + apiKey
                $.ajax({
                    url: url,
                    type: "GET",
                    success: function (data) {
                        console.log(data);
                        //return object type App
                        appData = data;
                    }
                })
            });

            //Create App
            $("#btnCreateApp").click(function () {
                username = sessionStorage.getItem("username");
                apiKey = sessionStorage.getItem("apiKey");
                appID = $("#txtAppID").val();
                appName = $("#txtAppName").val();
                type = $("#txtAppType").val();
                description = $("#txtAppDescription").val();

                var form_data = {
                    appID: appID,
                    appName: appName,
                    type: type,
                    description: description,
                    apiKey: apiKey
                }
                var url = apiPath + "/users/" + username + "/apps?apiKey=" + apiKey;
                $.ajax({
                    url: url,
                    type: "POST",
                    data: form_data,
                    success: function (data) {

                        console.log(data);

                        console.log("Created")

                    }
                })
            });

            // Change App
            $("#btnChangeApp").click(function () {
                username = sessionStorage.getItem("username");
                apiKey = sessionStorage.getItem("apiKey");

                appSearchChange = $("#txtAppIDSearchChange").val();
                console.log(appSearchChange)

                appName = $("#txtAppNameChange").val();
                type = $("#txtAppTypeChange").val();
                description = $("#txtAppDescriptionChange").val();

                var form_data = {
                    appName: appName,
                    type: type,
                    description: description,
                    apiKey: apiKey
                }
                var url = apiPath + "/users/" + username + "/apps/" + appSearchChange + "/?apiKey=" + apiKey
                $.ajax({
                    url: url,
                    type: "POST",
                    data: form_data,
                    success: function (data) {
                        console.log("changed")
                    }
                })
            });

            //Delete  
            $("#btnDeleteApp").click(function () {
                    username = sessionStorage.getItem("username");
                    apiKey = sessionStorage.getItem("apiKey");
                    appDelete = $("#txtAppIDDelete").val();
                    var url = apiPath + "/users/" + username + "/apps/" + appDelete + "?apiKey=" + apiKey;
                    $.ajax({
                            url: url,
                            type: "DELETE",
                            success: function (data,xhr, textStatus) {
                                console.log(data);
                            }

                    });
                });



            });