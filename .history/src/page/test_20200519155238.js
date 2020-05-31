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
        const {navigation} = this.props;
        return (
            <View style={{ marginTop: 30 }}>
                <WingBlank>
                {/* <Button type="warning">智能水杯盖</Button> */}
                </WingBlank>
                <WhiteSpace/>
                <View style={{ paddingHorizontal: 15 }}>
                    <Carousel
                      
                        selectedIndex={2}
                        autoplay
                        infinite
                    >
                        <View
                             style={[styles.containerHorizontal, { backgroundColor: 'red' }]}
                        >
                            <Text>智能水杯盖App说明</Text>
                        </View>
                        <View
                            style={[styles.containerHorizontal, { backgroundColor: '#DEF1E5' }]}
                        >
                            <Text>需要连接蓝牙</Text>
                        </View>
                    </Carousel>
                </View>
            </View>
        )
    }

}
const styles = StyleSheet.create({
    wrapper: {
      backgroundColor: '#fff',
    },
    containerHorizontal: {
      flexGrow: 1,
      alignItems: 'center',
      justifyContent: 'center',
      height: 150,
    },
    containerVertical: {
      flexGrow: 1,
      alignItems: 'center',
      justifyContent: 'center',
      height: 150,
    },
    text: {
      color: '#fff',
      fontSize: 36,
    },
  });
AppRegistry.registerComponent('test', () => Test);