random_vec(size, min, max) = map((x) -> min + (max - min) * x, rand(size))

sum_squares(itr) = reduce((v, elem) -> v + elem ^ 2, 0, itr)

function search(search_space, cost, num_itr)
	best = search_space()
	score = cost(best)
	for i = 1:(num_itr - 1)
		currSpace = search_space()
		currScore = cost(currSpace)
		if currScore < score
			best = currSpace
			score = currScore
		end
	end
	return score, best
end

search_space() = random_vec(2, -5.0, 5.0)
score, best = search(search_space, sum_squares, 100)
println("Score: " * string(score) * ", Solution: " * string(best))