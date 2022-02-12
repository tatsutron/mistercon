import React from "react";
import { ScrollView } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import GameListItem from "./GameListItem";

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
      {gameList.map((path) => {
        return (
          <GameListItem
            console={console}
            key={path}
            navigation={navigation}
            path={path}
          />
        );
      })}
    </ScrollView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default ConsoleScreen;
