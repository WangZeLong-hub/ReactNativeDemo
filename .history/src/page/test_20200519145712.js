import React, {Component} from 'react';
import HomePage from "./home";
import { StyleSheet, Text, View } from 'react-native';
import { Carousel } from '@ant-design/react-native';

export default class Test extends Component {

    onHorizontalSelectedIndexChange(index) {
        /* tslint:disable: no-console */
        console.log('horizontal change to', index);
      }
      onVerticalSelectedIndexChange(index) {
        /* tslint:disable: no-console */
        console.log('vertical change to', index);
      }
  render() {
    return (
        <View style={{ marginTop: 30 }}>
        <View style={{ paddingHorizontal: 15 }}>
  
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
              <Text>智能水杯盖</Text>
            </View>
            <View
              style={[styles.containerHorizontal, { backgroundColor: 'blue' }]}
            >
              <Text>智能水杯盖q</Text>
            </View>
            <View
              style={[
                styles.containerHorizontal,
                { backgroundColor: 'yellow' },
              ]}
            >
              <Text>智能水杯盖w</Text>
            </View>
            <View
              style={[styles.containerHorizontal, { backgroundColor: 'aqua' }]}
            >
              <Text>智能水杯盖2</Text>
            </View>
            <View
              style={[
                styles.containerHorizontal,
                { backgroundColor: 'fuchsia' },
              ]}
            >
              <Text>智能水杯盖s</Text>
            </View>
          </Carousel>
          </View>
          </View>
    );
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