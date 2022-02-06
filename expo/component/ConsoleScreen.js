import React from "react";
import { Button, SafeAreaView, ScrollView } from "react-native";

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
    <SafeAreaView style={{ flex: 1 }}>
      <ScrollView>
        {gameList.map((path, index) => {
          return (
            <Button
              key={index}
              title={util.getFilename({ path })}
              onPress={() => {
                navigation.navigate("Game", {
                  console,
                  path,
                });
              }}
            />
          );
        })}
      </ScrollView>
    </SafeAreaView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default ConsoleScreen;
