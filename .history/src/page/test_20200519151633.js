import React, {Component} from 'react';
import {
    View,
    Text, AppRegistry,
    StyleSheet,TextStyle,ViewStyle,
    
} from 'react-native';
import { Button  }from "antd-mobile-rn"
import HomePage from "./home";



export default class Test extends Component {
    render(){
        return (
            <View>
                <Text>注册的dsdsadsada页面</Text>
                <Button type="primary">智能水杯盖</Button>
                <Button type="warning">智能水杯盖</Button>
            </View>
        )
    }

}

AppRegistry.registerComponent('test', () => Test);