webpackJsonp([0],{BO1k:function(t,e,a){t.exports={default:a("fxRn"),__esModule:!0}},H6fd:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a("BO1k"),i=a.n(n),o={data:function(){return{input:"",currentPage1:5,total:-1,tableData:[],pageNum:1,pageSize:4}},methods:{up:function(t){var e=this,a=this;a.$axios.get("/video/shelf/"+t.vid,{}).then(function(t){200==t.data.status?(a.$notify({title:"成功",message:"上架成功",offset:0,duration:2e3}),a.$axios.post("/video/findPage",{pageNum:a.pageNum,pageSize:a.pageSize}).then(function(t){if(200==t.data.status){a.tableData=[],a.total=t.data.data.total;var n=!0,o=!1,s=void 0;try{for(var l,r=i()(t.data.data.list);!(n=(l=r.next()).done);n=!0){var u=l.value;a.tableData.push(u)}}catch(t){o=!0,s=t}finally{try{!n&&r.return&&r.return()}finally{if(o)throw s}}}else e.$notify({title:"失败",message:t.data.message,offset:0,duration:2e3})}).catch(function(t){this.$notify({title:"错误",message:"服务器繁忙",offset:0,duration:1500})})):e.$notify({title:"失败",message:t.data.message,offset:0,duration:2e3})}).catch(function(t){this.$notify({title:"错误",message:"服务器繁忙",offset:0,duration:1500})})},down:function(t){var e=this,a=this;a.$axios.get("/video/unShelf/"+t.vid,{}).then(function(t){200==t.data.status?(a.$notify({title:"成功",message:"下架成功",offset:0,duration:2e3}),a.$axios.post("/video/findPage",{pageNum:a.pageNum,pageSize:a.pageSize}).then(function(t){if(200==t.data.status){a.tableData=[],a.total=t.data.data.total;var n=!0,o=!1,s=void 0;try{for(var l,r=i()(t.data.data.list);!(n=(l=r.next()).done);n=!0){var u=l.value;a.tableData.push(u)}}catch(t){o=!0,s=t}finally{try{!n&&r.return&&r.return()}finally{if(o)throw s}}}else e.$notify({title:"失败",message:t.data.message,offset:0,duration:2e3})}).catch(function(t){this.$notify({title:"错误",message:"服务器繁忙",offset:0,duration:1500})})):e.$notify({title:"失败",message:t.data.message,offset:0,duration:2e3})}).catch(function(t){this.$notify({title:"错误",message:"服务器繁忙",offset:0,duration:1500})})},delete1:function(t){var e=this,a=this;a.$axios.post("/video/delete/"+t.vid,{}).then(function(t){200==t.data.status?(console.log(t),a.$notify({title:"成功",message:"视频删除成功",offset:0,duration:2e3}),a.$axios.post("/video/findPage",{pageNum:a.pageNum,pageSize:a.pageSize}).then(function(t){if(200==t.data.status){a.tableData=[],a.total=t.data.data.total;var n=!0,o=!1,s=void 0;try{for(var l,r=i()(t.data.data.list);!(n=(l=r.next()).done);n=!0){var u=l.value;a.tableData.push(u)}}catch(t){o=!0,s=t}finally{try{!n&&r.return&&r.return()}finally{if(o)throw s}}}else e.$notify({title:"失败",message:t.data.message,offset:0,duration:2e3})}).catch(function(t){this.$notify({title:"错误",message:"服务器繁忙",offset:0,duration:1500})})):e.$notify({title:"失败",message:t.data.message,offset:0,duration:2e3})}).catch(function(t){this.$notify({title:"错误",message:"服务器繁忙",offset:0,duration:1500})})},add:function(){this.$router.push("/newkind")},change:function(t){this.$router.push({path:"/changekind",query:{id:t.vid}})},search:function(){var t=this,e=this;e.$axios.post("/video/findPage",{vtitle:e.input}).then(function(a){if(200==a.data.status){e.tableData=[],e.total=a.data.data.total;var n=!0,o=!1,s=void 0;try{for(var l,r=i()(a.data.data.list);!(n=(l=r.next()).done);n=!0){var u=l.value;e.tableData.push(u)}}catch(t){o=!0,s=t}finally{try{!n&&r.return&&r.return()}finally{if(o)throw s}}}else t.$notify({title:"失败",message:a.data.message,offset:0,duration:2e3})}).catch(function(t){this.$notify({title:"错误",message:"服务器繁忙",offset:0,duration:1500})})},handleClick:function(t){console.log(t),this.$router.push({path:"/scancourse",query:{id:t.vid}})},handleSizeChange:function(t){console.log("每页 "+t+" 条")},handleCurrentChange:function(t){var e=this;console.log("当前页: "+t);var a=this;a.pageNum=t,a.tableData=[],a.$axios.post("/video/findPage",{pageNum:t,pageSize:4}).then(function(t){if(200==t.data.status){console.log(t),a.total=t.data.data.total;var n=!0,o=!1,s=void 0;try{for(var l,r=i()(t.data.data.list);!(n=(l=r.next()).done);n=!0){var u=l.value;a.tableData.push(u)}}catch(t){o=!0,s=t}finally{try{!n&&r.return&&r.return()}finally{if(o)throw s}}}else e.$notify({title:"失败",message:t.data.message,offset:0,duration:2e3})}).catch(function(t){this.$notify({title:"错误",message:"服务器繁忙",offset:0,duration:1500})})}},mounted:function(){var t=this,e=this;e.$axios.post("/video/findPage",{pageNum:e.pageNum,pageSize:e.pageSize}).then(function(a){if(200==a.data.status){console.log(a),e.total=a.data.data.total;var n=!0,o=!1,s=void 0;try{for(var l,r=i()(a.data.data.list);!(n=(l=r.next()).done);n=!0){var u=l.value;e.tableData.push(u)}}catch(t){o=!0,s=t}finally{try{!n&&r.return&&r.return()}finally{if(o)throw s}}}else t.$notify({title:"失败",message:a.data.message,offset:0,duration:2e3})}).catch(function(t){this.$notify({title:"错误",message:"服务器繁忙",offset:0,duration:1500})})}},s={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"contain"},[a("el-row",[a("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入内容"},model:{value:t.input,callback:function(e){t.input=e},expression:"input"}}),t._v(" "),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(e){t.search()}}},[t._v("搜索")]),t._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.add()}}},[t._v("新增")])],1),t._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.tableData,border:""}},[a("el-table-column",{attrs:{fixed:"",prop:"vtitle",label:"标题",width:"400"}}),t._v(" "),a("el-table-column",{attrs:{prop:"vurl",label:"缩略图",width:"120"}}),t._v(" "),a("el-table-column",{attrs:{prop:"vprice",label:"价格"}}),t._v(" "),a("el-table-column",{attrs:{prop:"vintegral",label:"积分价格"}}),t._v(" "),a("el-table-column",{attrs:{fixed:"right",label:"操作",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return["Y"==e.row.shelf?a("div",[a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){t.handleClick(e.row)}}},[t._v("查看")]),t._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){t.change(e.row)}}},[t._v("编辑")]),t._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){t.down(e.row)}}},[t._v("下架")]),t._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){t.delete1(e.row)}}},[t._v("删除")])],1):a("div",[a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){t.handleClick(e.row)}}},[t._v("查看")]),t._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){t.change(e.row)}}},[t._v("编辑")]),t._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){t.up(e.row)}}},[t._v("上架")]),t._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){t.delete1(e.row)}}},[t._v("删除")])],1)]}}])})],1),t._v(" "),a("el-pagination",{attrs:{"current-page":t.currentPage1,"page-size":4,layout:"total, prev, pager, next",total:t.total},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange,"update:currentPage":function(e){t.currentPage1=e}}})],1)},staticRenderFns:[]};var l=a("VU/8")(o,s,!1,function(t){a("HmtN")},"data-v-f025b44c",null);e.default=l.exports},HmtN:function(t,e){},fxRn:function(t,e,a){a("+tPU"),a("zQR9"),t.exports=a("g8Ux")},g8Ux:function(t,e,a){var n=a("77Pl"),i=a("3fs2");t.exports=a("FeBl").getIterator=function(t){var e=i(t);if("function"!=typeof e)throw TypeError(t+" is not iterable!");return n(e.call(t))}}});