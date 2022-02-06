import React from "react";
import { Button, ScrollView } from "native-base";

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
    <ScrollView>
      {gameList.map((path, index) => {
        return (
          <Button
            key={index}
            onPress={() => {
              navigation.navigate("Game", {
                console,
                path,
              });
            }}
          >
            {util.getFilename({ path })}
          </Button>
        );
      })}
    </ScrollView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default ConsoleScreen;
