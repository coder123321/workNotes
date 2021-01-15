<template>
  <div
    v-show="iframeBoxState"
    class="iframe-box">
    <iframe
      v-for="( value ) in iframeArr"
      v-show="value.show"
      :key="value.name"
      :src="value.src + value.params"
      :name="value.name"
      frameborder="0"
      width="100%"
      height="100%"
    />
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'IframeBox',
    data() {
      return {}
    },
    computed: {
      ...mapGetters({
        iframeArr: 'iframes/iframeArr',
        iframeBoxState: 'iframes/iframeBoxState'
      })
    }
  }
</script>

<style>
  .iframe-box {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    padding: 0 15px;
    z-index: 100;
    height: calc(100% - 32px);
    overflow-x: visible;
    overflow-y: visible;
  }
</style>
