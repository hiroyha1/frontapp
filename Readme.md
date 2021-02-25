# Lab 2 - Spring Boot アプリケーションのコンテナの ACR へのプッシュ

このラボでは Azure Pipelines を使用して Spring Boot アプリケーションのコンテナを作成し、Azure Container Registry へプッシュします。このラボを実施する前に [Lab 1](https://github.com/hiroyha1/echo) を完了してください。

## リポジトリの取得

1. ブラウザを起動し、Azure DevOps の対象のプロジェクトを開きます。
2. Repos の Files を開きます。
3. 画面上部のプロジェクト名が表示されたドロップダウンをクリックし、"Import Repository" を選択します。
4. Clone URL 欄に "https://github.com/hiroyha1/frontapp" と入力し、"Import" をクリックします。

## Variable group の作成

次の変数を持つ "acr-variable-group" という名前の Variable group を作成します。

| 名前 | 値 |
|--|--|
| acr_connection_name | ACR のサービス接続名 |
| repository_name | コンテナ イメージをプッシュするリポジトリ名 |

## パイプラインの作成

Spring Boot アプリケーションをビルドし、コンテナ化して ACR にプッシュします。

1. Pipelines の Pipelines を開きます。
2. "New pipeline" をクリックします。
3. "Where is your code?" と表示される画面で "Azure Repos Git" を選択します。
4. "Select a repository" と表示される画面で "frpntapp" を選択します。
5. "Configure your pipeline" と表示される画面で "Starter pipeline" を選択します。
展開先の ACR に対して適切なアクセス権を持っているユーザーの場合は、"Docker" を選択することで、ACR へ展開する YAML のテンプレートを生成することができます。
6. テンプレートの YAML ファイルが生成されます。この YAML ファイルは既定ではリポジトリのルート ディレクトリ配下に azure-pipelines.yml という名前で保存されます。
7. azure-pipelines.yml の内容を次のように書き換えます。
```yaml
trigger:
- master

pool:
  vmImage: ubuntu-latest

variables:
- group: feed-variable-group
- group: acr-variable-group

steps:
- task: MavenAuthenticate@0
  inputs:
    artifactsFeeds: $(artifacts_feed_name)
- task: Gradle@2
  inputs:
    workingDirectory: ''
    gradleWrapperFile: 'gradlew'
    gradleOptions: '-Xmx3072m'
    javaHomeOption: 'JDKVersion'
    jdkVersionOption: '1.8'
    jdkArchitectureOption: 'x64'
    publishJUnitResults: true
    testResultsFiles: '**/TEST-*.xml'
    tasks: 'build'
- task: Docker@2
  inputs:
    containerRegistry: $(acr_connection_name)
    repository: $(repository_name)
    command: 'build'
    Dockerfile: '**/Dockerfile'
- task: Docker@2
  inputs:
    containerRegistry: $(acr_connection_name)
    repository: $(repository_name)
    command: 'push'
```
8. "Save and run" をクリックすると YAML ファイルが保存され、パイプラインが実行されます。
9. Azure ポータルを開き、Azure Container Registry にコンテナ イメージがプッシュされていることを確認します。イメージのタグには既定ではビルド ID が付与されます。

ここまで完了したら [Lab 3](https://github.com/hiroyha1/frontapp-deploy) に進んでください。

## おまけ

### 問題 1.

コンテナ イメージのビルド後に Trivy によるセキュリティ スキャンを実施してみましょう。

ヒント: [Container Security Scanning with Trivy and Azure DevOps](https://lgulliver.github.io/container-security-scanning-with-trivy-in-azure-devops/)
