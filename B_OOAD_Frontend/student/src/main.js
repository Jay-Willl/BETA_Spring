import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import  router from './router'

import 'element-plus/theme-chalk/index.css'
import './style.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
import store from './store/index';


const app = createApp(App)
app.use(router)
app.use(store)
app.use(ElementPlus)
for (let iconName in ElementPlusIconsVue) {
    app.component(iconName, ElementPlusIconsVue[iconName])
}
app.mount('#app')
