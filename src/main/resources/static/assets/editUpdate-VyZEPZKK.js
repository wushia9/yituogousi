import{R as y,S as C,h as f,l as b,n as x,Q as T,H as v,o as E,I as k,P as i,J as _,F as w}from"./index-i72Bb-xp.js";import{i as m,E as V,a as D}from"./request-DpbL_-xe.js";import{E as I,T as R}from"./index.esm-DeeCtkG-.js";import{f as g}from"./index-CslebMiX.js";import{E as h}from"./index-DIF929JA.js";import{_ as B}from"./_plugin-vue_export-helper-DlAUqK2U.js";const U={components:{Editor:I,Toolbar:R,Check:g},setup(){const r=y(),a=C(),p=g;let e=!0;const o=f({articlesId:1,name:"",content:"",tag:"",createTime:new Date,updateTime:new Date}),n=f("<p>hello</p>"),l=t=>t.toISOString().replace("T"," ").replace("Z"," UTC");b(()=>{console.log(a.query.id),a.query.id!=null?(e=!1,m.get(`/api/articles/${a.query.id}`).then(t=>{t.data.data.updateTime=l(new Date),o.value=t.data.data,n.value=t.data.data.content})):(o.value.createTime=l(new Date(o.value.createTime)),o.value.updateTime=l(new Date(o.value.updateTime)))});const d={},c={placeholder:"请输入内容..."};x(()=>{const t=r.value;t!=null&&t.destroy()});const u=t=>{r.value=t},s=T();return{editorRef:r,valueHtml:n,mode:"default",toolbarConfig:d,editorConfig:c,handleCreated:u,article:o,check:p,submit:()=>{e?(o.value.content=n.value,m.post("/api/articles",o.value).then(t=>{h({message:"保存成功",type:"success"})})):(o.value.content=n.value,m.put("/api/articles/1",o.value).then(t=>{h({message:"保存成功",type:"success"}),s.push({path:"/"})}))}}}},H={class:"editor-container",style:{border:"1px solid #ccc"}},q={style:{display:"flex","justify-content":"flex-end"}};function O(r,a,p,e,o,n){const l=V,d=v("Toolbar"),c=v("Editor"),u=D;return E(),k(w,null,[i(l,{modelValue:e.article.name,"onUpdate:modelValue":a[0]||(a[0]=s=>e.article.name=s),style:{width:"240px"},placeholder:"Please input"},null,8,["modelValue"]),_("div",H,[i(d,{style:{"border-bottom":"1px solid #ccc"},editor:e.editorRef,defaultConfig:e.toolbarConfig,mode:e.mode},null,8,["editor","defaultConfig","mode"]),i(c,{style:{height:"500px","overflow-y":"hidden"},modelValue:e.valueHtml,"onUpdate:modelValue":a[1]||(a[1]=s=>e.valueHtml=s),defaultConfig:e.editorConfig,mode:e.mode,onOnCreated:e.handleCreated},null,8,["modelValue","defaultConfig","mode","onOnCreated"]),_("div",q,[i(u,{onClick:e.submit,type:"success",icon:e.check,circle:"",style:{display:"flex","margin-right":"12px","margin-bottom":"12px","z-index":"11"}},null,8,["onClick","icon"])])])],64)}const J=B(U,[["render",O],["__scopeId","data-v-2bc76d12"]]);export{J as default};