import React from "react";
import { Pressable, ScrollView, Text } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const config = require("../config.json");

///////////////////////////////////////////////////////////////////////////////
const ConsoleScreen = ({ navigation, route }) => {
  const { console } = route.params;
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
      const path = `${config.games}/${console.folder}`;
      const url = `http://${host}:${port}/list/${extensions}/${path}`;
      try {
        const response = await fetch(url);
        const text = await response.text();
        const lines = text.split("\n").filter((s) => s.length > 0);
        setGameList(lines.sort());
      } catch (error) {
        alert(error);
      }
    });
  }, [navigation]);

  return (
    <ScrollView bg="black">
      {gameList.map((path, index) => {
        return (
          <Pressable
            key={index}
            margin={2}
            onPress={() => {
              navigation.navigate("Game", {
                console,
                path,
              });
            }}
          >
            <Text fontSize="lg" color="white" marginLeft={16}>
              {util.getFilename({ path })}
            </Text>
          </Pressable>
        );
      })}
    </ScrollView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default ConsoleScreen;
