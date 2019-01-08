# SysproModelClasses

## 概要
シスプロ課題のモデル/IO周りのインタフェースを提供する

### パッケージ
- company
    CompanyManagerを含む.外部に提供するAPIを配置するpackage.

- model 
    データモデルを扱うpackage.
    基本的には以下の3パッケージ内でモデル定義を追加/変更/拡張する.
    
    - model.define
        モデルのデータ型を定義するpackage.
        同package内のAbstractModelDefineを継承したサブクラスを作成することで新たなModelDefineを定義する.

        - void defineModelName()
            モデル名(テーブル名)を定義する.
            ```
            this.modelName = "companies";
            ```

        - void defineColumns()
            モデル(テーブル)のcolumnを定義する.
            ```
            columns.put("id", new ModelOption(true));
            columns.put("name", new ModelOption(ModelType.STRING, false, 1));
            columns.put("location", new ModelOption(ModelType.STRING, false, 2));
            columns.put("type", new ModelOption(ModelType.STRING, false, 3));
            columns.put("description", new ModelOption(ModelType.STRING, true, 4));
            columns.put("paths", new ModelOption(Path.class));
            ```

        - void registerDependencies(ArrayList<String> dependencies)
            defineColumnsで外部キーを用いる時にはdependenciesListにモデル名を追加しなければならない.
            上記のdefineColumnsの例ではpathsとしてPathモデルのオブジェクトを外部キーとして指定しているため,pathsに依存することとなる
            ```
            dependencies.add("paths");
            ```

    - model.models
        モデルをJava内から用いるためのモデルクラスを定義するpackage.
        同package内のStorableを継承したサブクラスを作成することで新たなModelクラスを定義する.

        beanを用いているため厳格にgetter/settterを定義する必要がある.

        外部キーを用いるColumnはArrayListで定義する.


        - fields
        ```
            private String name;
            private ArrayList<Path> paths;
        ```

        - constructors
        ```

            public Company() {

            }

            public Company(int id, String name, String location, String type, String description, ArrayList<Path> paths) {
                setId(id);
                this.name = name;
                if(paths == null) this.paths = new ArrayList<Path>();
                else this.paths = paths;
            }

            public Company(String name, String location, String type, String description) {
                this.name = name;
                this.paths = new ArrayList<Path>();
            }
        ```

        - getter/setter
        ```
            public String getName() {
                return this.name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocation() {
                return this.location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getType() {
                return this.type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public ArrayList<Path> getPaths() {
                return this.paths;
            }

            public void setPaths(ArrayList<Path> paths) {
                this.paths = paths;
            }

            public void addPath(Path path) {
                this.paths.add(path);
            }

            public String getDescription() {
                return this.description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        ```

    - model.modelstores
        DBとしてモデルを検索する際に用いるクラス
        モデル操作のAPIを提供するクラスを定義する

        - public void includeForeignRecordIfNeeded(? extends Storable obj)
        ```
            @Override
            public void includeForeignRecordIfNeeded(Company obj) {
               //StoreProvider.getModelStore(ModelNameString)で該当モデルへのAccessorが取得できる 
               obj.setPaths(((PathModelStore)StoreProvider.getModelStore("paths")).findAllByCompanyId(obj.getId()));
            }

        ```

        - public String getModelName() 
        操作対象となるモデル名(テーブル名)を返す
        ```
            @Override
            public String getModelName() {
                return "companies";
            }
        ```

        - public Class<? extends Storable> getModelClass()
        操作対象となるモデルクラスを返す
        ```
            @Override
            public Class<Path> getModelClass() {
                return Path.class;
            }
        ```

        - 各種Accessor
        DB検索などを行うメソッドはここで定義する
        ```
            public Path findOneById(int id) {
                for(Path record : store.getAll()) {
                    if(id == record.getId()) return record;
                }
                return null;
            }
            
            public ArrayList<Path> findAllByCompanyId(int id) {
                ArrayList<Path> paths = new ArrayList<>();
                for(Path record : store.getAll()) {
                    if(record.getCompanyId() == id) paths.add(record);
                }
                return paths;
            }
        ```



