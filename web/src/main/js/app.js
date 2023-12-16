const React = require('react')
const { createRoot } = require('react-dom/client')
const client = require('./client')

class App extends React.Component {
	constructor(props) {
		super(props)
		this.state = {thought: {}}
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/thoughts/random'}).then(response => {
			this.setState({thought: response.entity})
		});
	}

	render() {
		console.log(this.state.thought)
		return (
			<>
				<p>{this.state.thought.title}</p>
			</>
		)
	}
}

const root = createRoot(document.getElementById('react'));
root.render(<App />);
