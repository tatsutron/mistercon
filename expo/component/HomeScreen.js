import React from "react";
import { Image, View } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import { MaterialCommunityIcons } from "@expo/vector-icons";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

///////////////////////////////////////////////////////////////////////////////
import ArcadeListScreen from "./ArcadeListScreen";
import ComputerListScreen from "./ComputerListScreen";
import ConsoleListScreen from "./ConsoleListScreen";

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
        name="ConsoleList"
        component={ConsoleListScreen}
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
        name="ArcadeList"
        component={ArcadeListScreen}
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
        name="ComputerList"
        component={ComputerListScreen}
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
