const path = require("node:path");

module.exports = {
	entry: "./src/main/js/app.js",
	devtool: false,
	cache: true,
	mode: "development",
	output: {
		path: __dirname,
		filename: "./src/main/resources/static/built/bundle.js",
	},
	module: {
		rules: [
			{
				test: path.join(__dirname, "."),
				exclude: /(node_modules)/,
				use: [
					{
						loader: "babel-loader",
						options: {
							presets: ["@babel/preset-env", "@babel/preset-react"],
						},
					},
				],
			},
		],
	},
};
