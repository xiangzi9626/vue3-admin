import {defineStore} from 'pinia'

export const tagStore = defineStore('t', {
    state: () => {
        return {
            tags: []
        }
    },
    getters: {},
    actions: {
        pushTags(data: any) {
            let b = true
            for (let i = 0; i < this.tags.length; i++) {
                //@ts-ignore
                if (this.tags[i].title === data.title) {
                    //@ts-ignore
                    this.tags[i] = data
                    b = false
                    break
                }
            }
            //@ts-ignore
            if (b) this.tags.push(data)
        }
    }
})
