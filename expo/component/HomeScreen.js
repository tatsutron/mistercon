import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { FontAwesome5, Ionicons } from "@expo/vector-icons";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

///////////////////////////////////////////////////////////////////////////////
import ArcadeListScreen from "./ArcadeListScreen";
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
            <Ionicons
              color={color}
              name="game-controller-outline"
              size={size}
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
            <FontAwesome5 name="coins" size={size} color={color} />
          ),
        }}
      />
    </Tab.Navigator>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default HomeScreen;
