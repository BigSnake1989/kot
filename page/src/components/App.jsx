import React,{ Component } from 'react';
import MainLayout from '../layouts/MainLayout/MainLayout';
import Home from './home/index';

class Main extends Component {
    render(){
        return (
            <MainLayout>
                <Home />
            </MainLayout>
        )
    }
}
export default Main;
