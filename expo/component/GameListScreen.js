import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { ScrollView } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import GameListItem from "./GameListItem";

///////////////////////////////////////////////////////////////////////////////
const GameListScreen = ({ navigation, route }) => {
  const { path, platform } = route.params;
  const [gameList, setGameList] = React.useState([]);

  React.useEffect(() => {
    return navigation.addListener("focus", async () => {
      const { host, port } = require("../config");
      const extensions = platform.format
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
        setGameList(
          entries.sort((a, b) => {
            return a.toLowerCase().localeCompare(b.toLowerCase());
          })
        );
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
            key={path}
            navigation={navigation}
            path={path}
            platform={platform}
          />
        );
      })}
    </ScrollView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default GameListScreen;
