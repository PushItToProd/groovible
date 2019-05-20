playbook {
    // a play with no name
    play {
        hosts = "webservers"
        vars {
            http_port = 80
            max_clients = 200
        }
        remote_user = "root"

        tasks {
            yum("ensure apache is at the latest version") {
                name = "httpd"
                state = "latest"
            }

            template("write the apache config file") {
                src = "/srv/httpd.j2"
                dest = "/etc/httpd.conf"

                notify "restart apache"
            }

            service("ensure apache is running") {
                name = "httpd"
                state = "started"
            }
        }

        handlers {
            service("restart apache") {
                name = "httpd"
                state = "restarted"
            }
        }
    }
}
