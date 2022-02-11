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
      const url = `http://${host}:${port}/list/rbf/${path}`;
      try {
        const response = await fetch(url);
        const text = await response.text();
        const lines = text.split("\n").filter((s) => s.length > 0);
        const arr = [];
        Object.keys(consoles).forEach((key) => {
          const console = consoles[key];
          const match = lines.find((path) => {
            const filename = util.getFilename({ path });
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
