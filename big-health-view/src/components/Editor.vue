<!-- eslint-disable prettier/prettier -->
<template>
    <div>
        <Toolbar style="border-bottom: 1px solid #eae8e8;" :editor="editor" :defaultConfig="toolbarConfig"
            :mode="mode" />
        <Editor :style="{ height: height, overflowY: 'hidden' }" v-model="content" :defaultConfig="editorConfig"
            :mode="mode" @onCreated="onCreated" />
    </div>
</template>
<script>
import Vue from "vue";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
export default Vue.extend({
  components: { Editor, Toolbar },
  props: {
    receiveContent: {
      type: String,
      default: "",
      required: true
    },
    height: {
      type: String,
      default: "calc(100vh - 100px)"
    },
    // 新增一个控制显示状态的props，用于触发重置
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      editor: null,
      content: "<p>创作内容</p>",
      toolbarConfig: {},
      editorConfig: {
        placeholder: "请输入内容...",
        MENU_CONF: {
          uploadImage: {
            server: "/api/personal-heath/v1.0/file/upload",
            fieldName: "file",
            maxFileSize: 10 * 1024 * 1024,
            maxNumberOfFiles: 10,
            // allowedFileTypes: ['image/*'],
            metaWithUrl: false,
            withCredentials: true,
            timeout: 10 * 1000,
            headers: {
              token: sessionStorage.getItem("token")
            },
            customInsert(res, insertFn) {
              insertFn(res.data, res.data, res.data);
            }
          },
          uploadVideo: {
            server: "/api/personal-heath/v1.0/file/upload",
            fieldName: "file",
            maxFileSize: 100 * 1024 * 1024,
            headers: {
              token: sessionStorage.getItem("token")
            },
            timeout: 60 * 1000,
            customInsert(res, insertFn) {
              insertFn(res.data, res.data);
            }
          }
        }
      },
      mode: "default",
      // 新增：标记编辑器是否已初始化
      isEditorReady: false
    };
  },
  methods: {
    onCreated(editor) {
      this.editor = Object.seal(editor);
      this.isEditorReady = true;
      //this.toolbarConfig.excludeKeys = ['group-video'];
    },
    // 新增：清空编辑器内容的方法
    clearEditorContent() {
      if (this.isEditorReady && this.editor) {
        // this.editor.txt.clear(); // 清空编辑器内容
        this.content = ""; // 同步清空绑定的data
      } else {
        // 如果编辑器还未初始化，直接重置content
        this.content = "";
      }
    }
  },
  watch: {
    // 监听visible变化，当显示时清空内容
    visible(newVal) {
      if (newVal) {
        // 如果编辑器已准备好，直接清空；否则等待初始化完成（在onCreated中处理）
        if (this.isEditorReady) {
          this.clearEditorContent();
        }
      }
    },
    receiveContent: {
      handler(v1, v2) {
        // console.log("接收内容：", v1);
        this.content = v1;
      },
      deep: true, // 启用深度监听
      immediate: true
    },
    content(newVal, oldVal) {
      this.$emit("on-listener", newVal);
    }
  },
  beforeDestroy() {
    const editor = this.editor;
    if (editor == null) return;
    editor.destroy();
  }
});
</script>
<style src="@wangeditor/editor/dist/css/style.css"></style>
<style scoped>
.line-number {
  display: block;
  margin-right: 10px;
  /* 以下样式确保行号不被选中或复制 */
  pointer-events: none;
  user-select: none;
  -webkit-user-select: none;
  color: #999;
  /* 行号颜色，可自定义 */
  text-align: right;
  /* 行号对齐方式 */
}
</style>
