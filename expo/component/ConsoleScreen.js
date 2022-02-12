import React from "react";
import { Pressable, ScrollView, Text } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const config = require("../config.json");

///////////////////////////////////////////////////////////////////////////////
const ConsoleScreen = ({ navigation, route }) => {
  const { console, path } = route.params;
  const [gameList, setGameList] = React.useState([]);

  React.useEffect(() => {
    return navigation.addListener("focus", async () => {
      const host = config.host;
      const port = config.port;
      const extensions = console.format
        .map((format) => {
          return format.extension;
        })
        .reduce((previousValue, currentValue) => {
          return `${previousValue}|${currentValue}`;
        });
      const url = `http://${host}:${port}/scan/${extensions}/${path}`;
      try {
        const response = await fetch(url);
        const entries = await response.json();
        setGameList(entries.sort());
      } catch (error) {
        alert(error);
      }
    });
  }, [navigation]);

  return (
    <ScrollView bg="black">
      {gameList.map((path, index) => {
        const isFolder = util.isFolder({ path });
        return (
          <Pressable
            key={index}
            margin={2}
            onPress={() => {
              if (isFolder) {
                navigation.push("Console", {
                  console,
                  path,
                });
              } else {
                navigation.navigate("Game", {
                  console,
                  path,
                });
              }
            }}
          >
            <Text fontSize="lg" color="white">
              {isFolder
                ? util.getFolderName({ path })
                : util.getFileName({ path })}
            </Text>
          </Pressable>
        );
      })}
    </ScrollView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default ConsoleScreen;
