import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { Button } from "native-base";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

///////////////////////////////////////////////////////////////////////////////
import GameDetailScreen from "../component/GameDetailScreen";
import GameListScreen from "../component/GameListScreen";
import TabNavigator from "./TabNavigator";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const Stack = createNativeStackNavigator();

///////////////////////////////////////////////////////////////////////////////
const StackNavigator = () => {
  return (
    <Stack.Navigator
      initialRouteName="Tabs"
      screenOptions={{
        headerStyle: {
          backgroundColor: "#171717",
        },
        headerTintColor: "white",
      }}
    >
      <Stack.Screen
        name="Tabs"
        component={TabNavigator}
        options={{
          headerShown: false,
          title: "Consoles",
        }}
      />
      <Stack.Screen
        name="GameList"
        component={GameListScreen}
        options={({ route }) => {
          const { platform } = route.params;
          return {
            title: platform.name,
          };
        }}
      />
      <Stack.Screen
        name="GameDetail"
        component={GameDetailScreen}
        options={({ route }) => {
          const { console, path } = route.params;
          return {
            title: util.getFileName({ path }),
            headerRight: () => (
              <Button
                _pressed={{
                  backgroundColor: "#000000",
                  borderColor: "white",
                }}
                borderColor="#28a4ea"
                onPress={() => {
                  util.loadGame({ console, path });
                }}
                variant="outline"
              >
                PLAY
              </Button>
            ),
          };
        }}
      />
    </Stack.Navigator>
  );
};

///////////////////////////////////////////////////////////////////////////////
export default StackNavigator;
