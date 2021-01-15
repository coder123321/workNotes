<template>
  <div class="app-container bg-white">&nbsp;</div>
</template>

<script>
  import iframeMixin from '@/utils/iframes/iframes-mixin'
  // diagrams
  export default {
    name: 'Diagrams',
    mixins: [iframeMixin],
    data() {
      return { PAGE_ID: 'DIAGRAMS' }
    }
  }
</script>
