import React from "react";

///////////////////////////////////////////////////////////////////////////////
import {
  Avatar,
  HStack,
  Pressable,
  ScrollView,
  Text,
  VStack,
} from "native-base";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

///////////////////////////////////////////////////////////////////////////////
import consoles from "../model/consoles";
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const config = require("../config.json");

///////////////////////////////////////////////////////////////////////////////
const Tab = createBottomTabNavigator();

///////////////////////////////////////////////////////////////////////////////
const PlatformListScreen = ({ navigation }) => {
  const [platformList, setPlatformList] = React.useState([]);

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
        setPlatformList(arr);
      } catch (error) {
        alert(error);
      }
    });
  }, [navigation]);

  return (
    <ScrollView bg="black">
      {platformList.map((platform, index) => {
        return (
          <VStack
            justifyContent="center"
            style={{
              height: 55,
            }}
          >
            <Pressable
              key={index}
              onPress={() => {
                navigation.navigate("GameList", {
                  path: `${require("../config").games}/${platform.folder}`,
                  platform,
                });
              }}
            >
              <HStack key={index} alignItems="center">
                <Avatar
                  bg="black"
                  borderWidth={1}
                  borderColor="white"
                  marginLeft={2}
                  source={platform.image}
                />
                <Text fontSize="lg" color="white" marginLeft={4}>
                  {platform.name}
                </Text>
              </HStack>
            </Pressable>
          </VStack>
        );
      })}
    </ScrollView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default PlatformListScreen;
