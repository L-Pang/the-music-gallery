// The Vue build version to load with the `import` command
import Vue from 'vue'
import App from './App'
import router from './router'

import Element from "element-ui";

import locale from 'element-ui/lib/locale/lang/en'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(Element, { locale });

Vue.config.productionTip = false

new Vue({
    router,
    render: h => h(App)
}).$mount("#app")