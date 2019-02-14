package generator.impl;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;

public class RequestGenerate extends AbstractCodeGenerator {

        public RequestGenerate(PsiClass psiClass) {
            super(psiClass);
        }

        /**
         * 生成网络请求类
         */
        @Override
        protected void startGenerate() {
            //生成绑定的类
            String className = "GetPendingHandleNumRequest";
            PsiClass requestClass = mClass.findInnerClassByName(className, false);
            System.out.println("classname"+requestClass);
            //如果没有创建
            if (requestClass == null) {
                requestClass = generateApiRequestClass(className, elementFactory);
                mClass.add(requestClass);
            }

            System.out.println("classname"+requestClass);
        }

        private PsiClass generateApiRequestClass(String className, PsiElementFactory elementFactory) {
          return elementFactory.createClassFromText("public class "+className+" extends ApiRequest<PendingHandlerNumBean> {\n" +
                    "\n" +
                    "    private String depotId;\n" +
                    "\n" +
                    "    private String storeId;\n" +
                    "\n" +
                    "    public "+className+"() {\n" +
                    "        super(\"rent.bos.getPendingHandleNum\");\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public Class<PendingHandlerNumBean> getDataClazz() {\n" +
                    "        return PendingHandlerNumBean.class;\n" +
                    "    }\n" +
                    "\n" +
                    "    public String getDepotId() {\n" +
                    "        return depotId;\n" +
                    "    }\n" +
                    "\n" +
                    "    public GetPendingHandleNumRequest setDepotId(String depotId) {\n" +
                    "        this.depotId = depotId;\n" +
                    "        return this;\n" +
                    "    }\n" +
                    "\n" +
                    "    public String getStoreId() {\n" +
                    "        return storeId;\n" +
                    "    }\n" +
                    "\n" +
                    "    public GetPendingHandleNumRequest setStoreId(String storeId) {\n" +
                    "        this.storeId = storeId;\n" +
                    "        return this;\n" +
                    "    }\n" +
                    "\n" +
                    "}",mClass).getInnerClasses()[0];

        }


    }