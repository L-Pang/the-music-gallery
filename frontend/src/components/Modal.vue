<template>
    <div class="modal" :class="{ 'is-active': isActive }">
        <div class="modal-background" @click="hide"></div>
        <div class="modal-content">
            <div class="box">
                <slot></slot>
            </div>
        </div>
        <button class="modal-close is-large" aria-label="close" @click="hide"></button>
    </div>
</template>

<script>
export default {
    name: 'Modal',
    props: {
        isActive: {
            type: Boolean,
            required: true
        }
    },
    methods: {
        hide() {
            this.$emit('close');
        }
    }
};
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  max-width: 600px;
  max-height: 500px;
  width: 30%;
  height: 70%;
  padding: 20px;
  border-radius: 4px;
  overflow: auto;
}
.modal.is-active {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 9999;
}

.modal-close {
    position: absolute;
    top: 0.5rem;
    right: 0.5rem;
    font-size: 1.5rem;
    background-color: transparent;
    border: none;
    cursor: pointer;
}

.box {
    margin-bottom: 0;
}
</style>
