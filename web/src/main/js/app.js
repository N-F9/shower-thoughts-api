const React = require('react')
const { createRoot } = require('react-dom/client')
const client = require('./client')

class App extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			count: 0,
			thought: {},
		}
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/thoughts/random'}).then(response => {
			this.setState({thought: response.entity})
		})
		client({method: 'GET', path: '/api/thoughts/count'}).then(response => {
			this.setState({count: response.entity})
		})
	}

	render() {
		console.log(this.state)
		const handleClick = async () => {
			try {
				client({method: 'GET', path: '/api/thoughts/random'}).then(response => {
					this.setState({thought: response.entity})
				})
				client({method: 'GET', path: '/api/thoughts/count'}).then(response => {
					this.setState({count: response.entity})
				})
			} catch (err) {
				console.log(err.message)
			}
		}
		return (
			<div style={{textAlign: 'center'}}>
				<h1>Shower Thoughts</h1>
				<p><a target="_blank" href=''>API Documentation</a></p>
				<div style={{
					position: 'absolute',
					top: '50%',
					left: '50%',
					transform: 'translate(-50%, -50%)'
				}}>
					<p>Total Thoughts: {this.state.count}</p>
					<pre style={{fontSize: '16px', whiteSpace: 'pre-wrap'}}><code>{this.state.thought.title}</code></pre>
					<p>By: <a target="_blank" href={'https://reddit.com/u/' + this.state.thought.author}>{this.state.thought.author}</a> | Link: <a target="_blank" href={'https://reddit.com' + this.state.thought.permalink}>Reddit</a> | Id: {this.state.thought.id}</p>
					<button style={{userSelect: 'none'}} onClick={handleClick}>Get A New One</button>
				</div>
			</div>
		)
	}
}

const root = createRoot(document.getElementById('react'));
root.render(<App />);
