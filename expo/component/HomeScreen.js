import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { MaterialCommunityIcons } from "@expo/vector-icons";
import { Image } from "native-base";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

///////////////////////////////////////////////////////////////////////////////
import arcades from "../model/arcades";
import consoles from "../model/consoles";

///////////////////////////////////////////////////////////////////////////////
import ComputerListScreen from "./ComputerListScreen";
import GameListScreen from "./GameListScreen";
import PlatformListScreen from "./PlatformListScreen";

///////////////////////////////////////////////////////////////////////////////
const Tab = createBottomTabNavigator();

///////////////////////////////////////////////////////////////////////////////
const HomeScreen = () => {
  return (
    <Tab.Navigator
      initialRouteName="ConsoleList"
      mode="modal"
      screenOptions={{
        animationEnabled: false,
        headerStyle: {
          backgroundColor: "#171717",
        },
        headerTintColor: "white",
        tabBarStyle: {
          backgroundColor: "black",
        },
        tabBarActiveTintColor: "#28a4ea",
      }}
    >
      <Tab.Screen
        component={PlatformListScreen}
        initialParams={{
          model: consoles,
          path: require("../config").console,
        }}
        name="ConsoleList"
        options={{
          title: "Consoles",
          tabBarIcon: ({ color, size }) => (
            <MaterialCommunityIcons
              name="gamepad-square"
              size={size}
              color={color}
            />
          ),
        }}
      />
      <Tab.Screen
        component={GameListScreen}
        initialParams={{
          path: require("../config").arcade,
          platform: arcades[0],
        }}
        name="ArcadeList"
        options={{
          title: "Arcades",
          tabBarIcon: ({ color, size }) => (
            <Image
              alt="Icon"
              fadeDuration={0}
              source={require("../assets/joystick-icon.png")}
              style={{ width: size, height: size }}
              tintColor={color}
            />
          ),
        }}
      />
      <Tab.Screen
        component={ComputerListScreen}
        name="ComputerList"
        options={{
          title: "Computers",
          tabBarIcon: ({ color, size }) => (
            <MaterialCommunityIcons name="keyboard" size={size} color={color} />
          ),
        }}
      />
    </Tab.Navigator>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default HomeScreen;
