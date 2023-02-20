<template>
    <div class="header">
        <div class="title">{{ title }}</div>
        <div class="buttons">
            <button class="button" @click="add">{{ button }}</button>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        title: {
            type: String,
            required: true
        },
        button: {
            type: String,
            required: true
        }
    },
    methods: {
        add() {
            this.$emit("add");
        }
    },
}
</script>

<style scoped>
.header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: #fff;
    padding: 20px 40px 20px 40px;
}

.title {
    font-size: 24px;
    font-weight: bold;
}

.buttons {
    display: flex;
    justify-content: flex-end;
}

.button {
    background-color: #343a40;
    color: #fff;
    border: none;
    padding: 10px;
    border-radius: 5px;
    margin-left: 10px;
    cursor: pointer;
}

.button:hover {
    background-color: #f9ad28;
}

.button:active {
    background-color: #f9ad28;
}
</style>
