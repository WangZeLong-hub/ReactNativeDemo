import React, {Component} from 'react';
import {
    View,
    Text, AppRegistry,
    StyleSheet,TextStyle,ViewStyle,
} from 'react-native';
import HomePage from "./home";
import { Button } from 'antd-mobile-rn';


export default class Test extends Component {
    render(){
        return (
            <View>
                <Text>注册的dsdsadsada页面</Text>
                <Button>Start</Button>
            </View>
        )
    }

}

AppRegistry.registerComponent('test', () => Test);