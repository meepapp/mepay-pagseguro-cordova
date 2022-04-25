# mepay-pagseguro-cordova
## Cordova plugin to manage pagseguro sdk
This plugin enables the native pagseguro sdks to cordova enviroment.

## How to install?

Use cordova plugin manager to install

`> cordova plugin add com.meep.pay.pagseguro`

## Post install

After the instalation, we need to copy some libs to another path.
Copy the folder `libs` on `plugins\com.meep.pay.pagseguro\src\android\` to `platforms\android\app\`

Enables the kotlin plugin on your project adding the following line to your `config.xml` file:
`<preference name="GradlePluginKotlinEnabled" value="true" />`

Now just build your project to download the packages on native side
`> cordova build android`

and run
`> cordova run android`


## How to use?

To access the plugin you need to call `cordova.plugins.MePay` on your project.