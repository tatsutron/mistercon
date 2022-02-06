import React from "react";
import { Button, SafeAreaView, ScrollView } from "react-native";

///////////////////////////////////////////////////////////////////////////////
import consoles from "../model/consoles.js";
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const config = require("../config.json");

///////////////////////////////////////////////////////////////////////////////
const HomeScreen = ({ navigation }) => {
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
    <SafeAreaView style={{ flex: 1 }}>
      <ScrollView>
        {consoleList.map((console, index) => {
          return (
            <Button
              key={index}
              title={console.name}
              onPress={() => {
                navigation.navigate("Console", {
                  console,
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
export default HomeScreen;
