const React = require("react");
const { createRoot } = require("react-dom/client");
const client = require("./client");
const _ = require("lodash");

class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			count: 0,
			thought: {},
			over_18: false,
			from: "",
			to: "",
		};
	}

	componentDidMount() {
		this.getData();
	}

	getData() {
		client({
			method: "GET",
			path: `/api/thoughts/random?over_18=${this.state.over_18}&from=${this.state.from}&to=${this.state.to}`,
		}).then((response) => {
			this.setState({ thought: response.entity });
		});
		client({ method: "GET", path: "/api/thoughts/count" }).then((response) => {
			this.setState({ count: response.entity });
		});
	}

	render() {
		const handleClick = async () => {
			try {
				this.getData();
			} catch (err) {
				console.log(err.message);
			}
		};

		return (
			<div style={{ textAlign: "center" }}>
				<h1>🚿 Shower Thoughts</h1>
				<p>
					<a
						target="_blank"
						href="https://docs.nickf.me/projects/shower%20thoughts/"
						rel="noreferrer"
					>
						API Documentation
					</a>{" "}
					|{" "}
					<a
						target="_blank"
						href="https://github.com/N-F9/shower-thoughts-api"
						rel="noreferrer"
					>
						Github
					</a>{" "}
					| <span>v0.0.2</span>
				</p>
				<div
					style={{
						position: "absolute",
						top: "50%",
						left: "50%",
						transform: "translate(-50%, -50%)",
					}}
				>
					<p>Total Thoughts: {this.state.count}</p>
					{Object.keys(this.state.thought).length !== 0 ? (
						<>
							<pre style={{ fontSize: "16px", whiteSpace: "pre-wrap" }}>
								<code>{_.unescape(this.state.thought.title)}</code>
							</pre>
							<p>
								By:{" "}
								<a
									target="_blank"
									href={`https://reddit.com/u/${this.state.thought.author}`}
									rel="noreferrer"
								>
									{this.state.thought.author}
								</a>{" "}
								| Link:{" "}
								<a
									target="_blank"
									href={`https://reddit.com${this.state.thought.permalink}`}
									rel="noreferrer"
								>
									Reddit
								</a>{" "}
								| Id: {this.state.thought.id}
							</p>
						</>
					) : (
						<>
							<p>Unable to get a thought!</p>
						</>
					)}

					<div style={{ marginBottom: "8px" }}>
						Over 18:{" "}
						<input
							type="checkbox"
							onClick={() => {
								this.state.over_18 = !this.state.over_18;
							}}
							value={this.state.over_18}
						/>
					</div>
					{/* biome-ignore lint/a11y/useButtonType: for the water.css styling */}
					<button style={{ userSelect: "none" }} onClick={handleClick}>
						Get A New One
					</button>
				</div>
			</div>
		);
	}
}

const root = createRoot(document.getElementById("react"));
root.render(<App />);
