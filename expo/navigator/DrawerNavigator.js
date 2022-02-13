import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { createDrawerNavigator } from "@react-navigation/drawer";

///////////////////////////////////////////////////////////////////////////////
import NativeStackNavigator from "./NativeStackNavigator";

///////////////////////////////////////////////////////////////////////////////
const Drawer = createDrawerNavigator();

///////////////////////////////////////////////////////////////////////////////
const DrawerNavigator = () => {
  return (
    <Drawer.Navigator>
      <Drawer.Screen
        component={NativeStackNavigator}
        name="NativeStackNavigator"
        options={{
          headerShown: false,
          title: "Library",
        }}
      />
    </Drawer.Navigator>
  );
};

///////////////////////////////////////////////////////////////////////////////
export default DrawerNavigator;
