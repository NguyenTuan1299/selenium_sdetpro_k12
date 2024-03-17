# Disclaimer
In this class we are using selenium version 4.16.1. Please use the same version.
In case you want to use a different version, please follow the guide at that time to avoid any unexpected issue.

# Selected Grid Roles
At this version, there are 3 roles of grid: Standalone, Hub and Nodes, Distributed.
The roles that is used widely is the [Hub and Nodes](https://www.selenium.dev/documentation/grid/getting_started/#hub-and-node).
We will use that.


# Download
Please download below file and put it into folder(can be anywhere on your machine):
* Please download **Selenium Server (Grid)** (NOT Selenium Clients and WebDriver Language Bindings)

# NOTE
If you just want to start quickly, to have hub and nodes and all component to try grid immediately, you can do:
* Go the folder that contains the selenium server jar file
* Execute this command:
```shell
java -jar selenium-server-4.16.1.jar standalone --selenium-manager true
```

# Start HUB
Go to the folder that contains the downloaded files above, then execute below command
```shell
java -jar selenium-server-4.16.1.jar hub
```
# Register Node
## Same machine with hub
```shell
java -jar selenium-server-4.16.1.jar node --hub http://localhost:4444 --selenium-manager true
```

## From a different machine
```shell
java -jar selenium-server-4.16.1.jar node --hub http://<hub-ip>:4444 --selenium-manager true
```
**When the Hub is not using the default ports, the --publish-events and --subscribe-events flags are needed.**