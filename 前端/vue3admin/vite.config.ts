import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from "path"
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    server: {
         port:80,
    },
    resolve:{
        alias:{
            "@":resolve(__dirname,'src')
        }
    },
    build: {
        //target: 'es2015',
        chunkSizeWarningLimit: 2000,//打包体积设置kb单位
        /*rollupOptions: {
            input: {
                main: './index.html'
            },
            output: {
                manualChunks(id: any) {
                    if (id.includes('node_modules')) {
                        return id.toString().split('node_modules/')[1].split('/')[0].toString()
                    }
                }
            }
        }*/
    }
})
