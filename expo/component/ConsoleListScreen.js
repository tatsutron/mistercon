import React from "react";
import { Avatar, HStack, Pressable, ScrollView, Text } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

///////////////////////////////////////////////////////////////////////////////
import consoles from "../model/consoles";
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const config = require("../config.json");

///////////////////////////////////////////////////////////////////////////////
const Tab = createBottomTabNavigator();

///////////////////////////////////////////////////////////////////////////////
const ConsoleListScreen = ({ navigation }) => {
  const [consoleList, setConsoleList] = React.useState([]);

  React.useEffect(() => {
    return navigation.addListener("focus", async () => {
      const host = config.host;
      const port = config.port;
      const path = config.console;
      const url = `http://${host}:${port}/scan/rbf/${path}`;
      try {
        const response = await fetch(url);
        const entries = await response.json();
        const arr = [];
        Object.keys(consoles).forEach((key) => {
          const console = consoles[key];
          const match = entries.find((path) => {
            const filename = util.getFileName({ path });
            return filename.startsWith(console.core);
          });
          if (match) {
            arr.push(console);
          }
        });
        setConsoleList(arr);
      } catch (error) {
        alert(error);
      }
    });
  }, [navigation]);

  return (
    <ScrollView bg="black">
      {consoleList.map((console, index) => {
        return (
          <Pressable
            key={index}
            margin={2}
            onPress={() => {
              navigation.navigate("Console", {
                console,
                path: `${config.games}/${console.folder}`,
              });
            }}
          >
            <HStack key={index} alignItems="center">
              <Avatar
                bg="black"
                borderWidth={1}
                borderColor="white"
                source={console.image}
              />
              <Text fontSize="lg" color="white" marginLeft={4}>
                {console.name}
              </Text>
            </HStack>
          </Pressable>
        );
      })}
    </ScrollView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default ConsoleListScreen;
