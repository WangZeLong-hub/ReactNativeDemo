/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import {
    AsyncStorage,
    StyleSheet,
    Image,
    View,
    Text,
    TouchableOpacity,
    ActivityIndicator,
    DrawerLayoutAndroid,
    NativeModules,//引入的模块

} from 'react-native';
import Storage from 'react-native-storage';
import HttpUtils from "../utils/HttpUtils"
import { pxToDp } from "../utils/SizeUtils"
import { Button, Carousel, WhiteSpace, WingBlank } from "antd-mobile-rn"



export default class Home extends Component {




    jumpNative() {
        //调用原生方法
        NativeModules.IntentMoudle.startActivityFromJS("MainActivity", null);
    }

    render() {
        const { navigation } = this.props;

        return (
            <View>
                <View style={styles.container}>

                    <TouchableOpacity style={styles.bt} activeOpacity={0.5}
                        onPress={() => {
                            navigation.navigate('TestPage');
                        }}>
                        <Text style={styles.bt_text}>
                            注册的子页面
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.bt} activeOpacity={0.5}
                        onPress={() => {
                            this.jumpNative();
                        }}>
                        <Text style={styles.bt_text}>
                            回到首页
                        </Text>
                    </TouchableOpacity>
                </View>
            
            <View style={{ marginTop: 30 }}>
                <WingBlank>
                    {/* <Button type="warning">智能水杯盖</Button> */}
                </WingBlank>
                <WhiteSpace />

                <View style={{ paddingHorizontal: 15 }}>
                    <Carousel
                        autoplay
                        infinite
                    >
                        <View
                            style={[styles.containerHorizontal, { backgroundColor: 'red' }]}
                        >
                            <Text>获取位置权限</Text>
                        </View>
                        <View
                            style={[styles.containerHorizontal, { backgroundColor: '#DEF1E5' }]}
                        >
                            <Text>打开蓝牙</Text>
                        </View>
                        <View
                            style={[styles.containerHorizontal, { backgroundColor: '#FFB6C1' }]}
                        >
                            <Text>点击搜索刷新按钮</Text>
                        </View>
                        <View
                            style={[styles.containerHorizontal, { backgroundColor: '#4169E1' }]}
                        >
                            <Text>选择蓝牙适配器</Text>
                        </View>
                        <View
                            style={[styles.containerHorizontal, { backgroundColor: '#3CB371' }]}
                        >
                            <Text>选择末尾的服务UUID</Text>
                        </View>
                        <View
                            style={[styles.containerHorizontal, { backgroundColor: '#DAA520' }]}
                        >
                            <Text>个性化配置交互</Text>
                        </View>
                        <View
                            style={[styles.containerHorizontal, { backgroundColor: '#DAA520' }]}
                        >
                            <Text>可回到首页</Text>
                        </View>

                    </Carousel>
                </View>
                <WingBlank size="lg">
            <Steps current={1}>
              <Step
                key={0}
                title="获取位置权限"
                description="需要到手机设置里获取"
                status="finish"
              />
              <Step
                key={1}
                title="打开蓝牙"
                description="手机蓝牙最低版本为4.0"
                status="progress"
              />
              <Step
                key={2}
                title="点击搜索刷新按钮"
                description="蓝牙列表展示搜索结果"
                status="progress"
                icon={<Icon name="down" size={20} color="white" />}
              />
              <Step
                key={3}
                title="选择蓝牙适配器"
                description="选择名称进入服务选择"
                status="progress"
                icon={<Icon name="down" size={20} color="white" />}
              />
              <Step
                key={4}
                title="选择末尾的服务UUID"
                description="选择收发数据的UUID"
                status="progress"
                icon={<Icon name="down" size={20} color="white" />}
              />
              <Step
                key={5}
                title="个性化配置交互"
                description="点击按钮发送配置信息"
                status="progress"
                icon={<Icon name="down" size={20} color="white" />}
              />
              <Step
                key={6}
                title="可回到首页"
                description="重新选择蓝牙连接"
                status="progress"
                icon={<Icon name="down" size={20} color="white" />}
              />
            </Steps>
            
          </WingBlank>
            </View>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        alignItems: 'center',
        flexDirection: 'row',
        flexWrap: 'wrap'
    },
    bt: {
        width: pxToDp(180),
        height: 50,
        backgroundColor: '#0af',
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: pxToDp(5),
        marginLeft: pxToDp(5),
        marginTop: pxToDp(5),
    },
    bt_text: {
        color: '#fff',
    },
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

