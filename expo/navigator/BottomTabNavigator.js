import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { MaterialCommunityIcons } from "@expo/vector-icons";
import { Image } from "native-base";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

///////////////////////////////////////////////////////////////////////////////
import arcades from "../model/arcades";
import computers from "../model/computers";
import consoles from "../model/consoles";
import handhelds from "../model/handhelds";

///////////////////////////////////////////////////////////////////////////////
import GameListScreen from "../component/GameListScreen";
import PlatformListScreen from "../component/PlatformListScreen";

///////////////////////////////////////////////////////////////////////////////
const Tab = createBottomTabNavigator();

///////////////////////////////////////////////////////////////////////////////
const BottomTabNavigator = () => {
  return (
    <Tab.Navigator
      screenOptions={{
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
        component={PlatformListScreen}
        initialParams={{
          model: handhelds,
          path: require("../config").console,
        }}
        name="HandheldList"
        options={{
          title: "Handhelds",
          tabBarIcon: ({ color, size }) => (
            <MaterialCommunityIcons
              name="nintendo-game-boy"
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
        component={PlatformListScreen}
        initialParams={{
          model: computers,
          path: require("../config").computer,
        }}
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

///////////////////////////////////////////////////////////////////////////////
export default BottomTabNavigator;
