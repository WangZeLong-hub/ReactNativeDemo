import {createStackNavigator, createBottomTabNavigator, createTabNavigator} from 'react-navigation'
import React from 'react'
import HomePage from "../page/home"
import TestPage from "../page/test"
import Component11 from "../page/Component11"
import FlatListPage from "../page/FlatListPage"
import HookPage from "../page/HookPage"
import InputInfoPage from "../page/InputInfoPage"
import KeyboardAvoidingViewPage from "../page/KeyboardAvoidingViewPage"
import PlaceAnOrder from "../page/PlaceAnOrder"
import SomeComponent from "../page/SomeComponent"
import TextPage from "../page/TextPage"
import TouchablePage from "../page/TouchablePage"



export const AppStackNavigator = createStackNavigator({
    HomePage: {
        screen: HomePage,
        navigationOptions: {
            title: "首页",
            // headerRight: (
            //     <Text style={{marginRight: 20}}>哈哈</Text>
            // ),
        },
    },
    TestPage:{
        screen: TestPage,
        navigationOptions: {
            title: "智能水杯盖使用提示",
        },
    },
    Component11:{
        screen: Component11,
        navigationOptions: {
            title: "一些组件",
        },
    },
    
}, {});