import React from "react";
import {
    Button,
} from "react-native";

///////////////////////////////////////////////////////////////////////////////
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

///////////////////////////////////////////////////////////////////////////////
import util from "./util/util";

///////////////////////////////////////////////////////////////////////////////
import ConsoleScreen from "./component/ConsoleScreen";
import GameScreen from "./component/GameScreen";
import HomeScreen from "./component/HomeScreen";

///////////////////////////////////////////////////////////////////////////////
const Stack = createNativeStackNavigator();

///////////////////////////////////////////////////////////////////////////////
const App = () => {

    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Home">
                <Stack.Screen
                    name="Home"
                    component={HomeScreen}
                />
                <Stack.Screen
                    name="Console"
                    component={ConsoleScreen}
                    options={({ route }) => {
                        const { console } = route.params;
                        return {
                            title: console.name,
                        };
                    }}
                />
                <Stack.Screen
                    name="Game"
                    component={GameScreen}
                    options={({ route }) => {
                        const { console, path } = route.params;
                        return {
                            title: util.getFilename({ path }),
                            headerRight: () => (
                                <Button
                                    onPress={() => {
                                        util.loadGame({ console, path });
                                    }}
                                    title="Play"
                                />
                            ),
                        };
                    }}
                />
            </Stack.Navigator>
        </NavigationContainer>
    );
};

///////////////////////////////////////////////////////////////////////////////
export default App;

