import React, {Component} from 'react';
import {
    View,
    Text, AppRegistry,
    StyleSheet,TextStyle,ViewStyle,
} from 'react-native';
import HomePage from "./home";
import { Button } from 'antd-mobile-rn';
import { Carousel, WingBlank } from 'antd-mobile-rn';

export default class Test extends Component {

   state = {
    data: ['1', '2', '3'],
    imgHeight: 176,
  }
  componentDidMount() {
    // simulate img loading
    setTimeout(() => {
      this.setState({
        data: ['AiyWuByWklrrUDlFignR', 'TekJlZRVCjLFexlOCuWn', 'IJOtIlfsYdTyaDTRVrLI'],
      });
    }, 100);
  }
  render() {
    return (
      <WingBlank>
        <Carousel 
          frameOverflow="visible"
          cellSpacing={10}
          slideWidth={0.8}
          autoplay
          infinite
          beforeChange={(from, to) => console.log(`slide from ${from} to ${to}`)}
          afterChange={index => this.setState({ slideIndex: index })}
        >
          {this.state.data.map((val, index) => (
            <a
              key={val}
              style={{
                display: 'block',
                position: 'relative',
                top: this.state.slideIndex === index ? -10 : 0,
                height: this.state.imgHeight,
                boxShadow: '2px 1px 1px rgba(0, 0, 0, 0.2)',
              }}
            >
              智能水杯盖
            </a>
          ))}
        </Carousel>
      </WingBlank>
    );
  }

}

AppRegistry.registerComponent('test', () => Test);