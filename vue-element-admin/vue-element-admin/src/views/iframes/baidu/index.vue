<template>
  <div class="app-container bg-white">&nbsp;</div>
</template>

<script>
  import iframeMixin from '@/utils/iframes/iframes-mixin'
  // 百度
  export default {
    name: 'Baidu',
    mixins: [iframeMixin],
    data() {
      return { PAGE_ID: 'BAI_DU' }
    }
  }
</script>
