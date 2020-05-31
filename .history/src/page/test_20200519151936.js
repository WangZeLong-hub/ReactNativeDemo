import React, { Component } from 'react';
import {
    View,
    Text, AppRegistry,
    StyleSheet, TextStyle, ViewStyle,

} from 'react-native';
import { Button, Carousel } from "antd-mobile-rn"
import HomePage from "./home";



export default class Test extends Component {
    render() {
        return (
            <View>
                <View>
                    <Text>注册的dsdsadsada页面</Text>
                    <Button type="primary">智能水杯盖</Button>
                    <Button type="warning">智能水杯盖</Button>
                </View>
                <View>
                    <Carousel
                        style={styles.wrapper}
                        selectedIndex={2}
                        autoplay
                        infinite
                        afterChange={this.onHorizontalSelectedIndexChange}
                    >
                        <View
                            style={[styles.containerHorizontal, { backgroundColor: 'red' }]}
                        >
                            <Text>智能水杯盖App说明</Text>
                        </View>
                    </Carousel>
                </View>
            </View>
        )
    }

}

AppRegistry.registerComponent('test', () => Test);