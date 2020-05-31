import React, { Component } from 'react';
import {
    View,
    Text, AppRegistry,
    StyleSheet, TextStyle, ViewStyle,

} from 'react-native';
import { Button, Carousel,WhiteSpace,WingBlank } from "antd-mobile-rn"
import HomePage from "./home";



export default class Test extends Component {
    render() {
        return (
            <View>
                <View>
                    <Text>注册的dsdsadsada页面</Text>
                    <Button type="primary">智能水杯盖</Button>
                    
                </View>
                <WingBlank>
                <Button type="warning">智能水杯盖</Button>
                </WingBlank>
                <WhiteSpace/>
                <View>
                    <Carousel
                      
                        selectedIndex={2}
                        autoplay
                        infinite
                    >
                        <View
                            
                        >
                            <Text>智能水杯盖App说明</Text>
                        </View>
                        <View
                           
                        >
                            <Text>需要连接蓝牙</Text>
                        </View>
                    </Carousel>
                </View>
            </View>
        )
    }

}

AppRegistry.registerComponent('test', () => Test);